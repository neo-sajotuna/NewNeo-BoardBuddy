package com.sparta.newneoboardbuddy.domain.card.service;

import com.sparta.newneoboardbuddy.common.dto.AuthUser;
import com.sparta.newneoboardbuddy.common.exception.InvalidRequestException;
import com.sparta.newneoboardbuddy.common.exception.NotFoundException;
import com.sparta.newneoboardbuddy.domain.card.dto.request.CardCreateRequest;
import com.sparta.newneoboardbuddy.domain.card.dto.request.CardUpdateRequest;
import com.sparta.newneoboardbuddy.domain.card.dto.response.CardCreateResponse;
import com.sparta.newneoboardbuddy.domain.card.dto.response.CardUpdateResponse;
import com.sparta.newneoboardbuddy.domain.card.entity.Card;
import com.sparta.newneoboardbuddy.domain.card.repository.CardRepository;
import com.sparta.newneoboardbuddy.domain.cardActivityLog.entity.CardActivityLog;
import com.sparta.newneoboardbuddy.domain.cardActivityLog.enums.Action;
import com.sparta.newneoboardbuddy.domain.cardActivityLog.repository.CardActivityLogRepository;
import com.sparta.newneoboardbuddy.domain.list.entity.BoardList;
import com.sparta.newneoboardbuddy.domain.member.entity.Member;
import com.sparta.newneoboardbuddy.domain.member.enums.MemberRole;
import com.sparta.newneoboardbuddy.domain.member.rpository.MemberRepository;
import com.sparta.newneoboardbuddy.domain.member.service.MemberService;
import com.sparta.newneoboardbuddy.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {

    private final BoardListRepository boardListRepository;
    private final CardRepository cardRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final CardActivityLogRepository cardActivityLogRepository;

    public CardCreateResponse createCard(Long listId, AuthUser authUser, CardCreateRequest request) {
        User user = User.fromUser(authUser);

        Member member = memberService.memberPermission(authUser, user.getId(), request.getWorkspaceId());

        // 읽기 전용 유저 생성 못하게 예외처리 해야함
        if (member.getMemberRole() == MemberRole.READ_ONLY_MEMBER){
            throw new InvalidRequestException("읽기 전용 멤버는 카드를 생성할 수 없습니다.");
        }

        // 카드 추가될 리스트 조회
        BoardList list = boardListRepository.findById(listId).orElseThrow(() ->
                new InvalidRequestException("list not found"));

        // 담당자 멤버 ID 를 받아서 조회
         Member assignedMember = memberRepository.findById(request.getMemberId()).orElseThrow(()-> new NotFoundException("멤버가 없습니다."));


        Card newCard = new Card(
                request.getCardTitle(),
                request.getCardContent(),
                request.getStartedAt(),
                request.getFinishedAt(),
                assignedMember,
                user,
                list
        );
        Card savedCard = cardRepository.save(newCard);

        logCardActivity(savedCard, Action.CREATED, "제목: " + savedCard.getCardTitle()  +
                ", 내용 : " + savedCard.getCardContent() +
                ", 관리 멤버 :" + " -> " + assignedMember);

        return new CardCreateResponse(
                savedCard.getCardId(),
                savedCard.getCardTitle(),
                savedCard.getCardContent(),
                savedCard.getStartedAt(),
                savedCard.getFinishedAt(),
                savedCard.getMember().getMemberId()
        );

    }


    public CardUpdateResponse updateCard(Long cardId, AuthUser authUser, CardUpdateRequest request) {
        Card card = cardRepository.findById(cardId).orElseThrow(()->
                new NoSuchElementException("카드를 찾을 수 없다."));

        // 수정 전 현재 상태
        String oldTitle = card.getCardTitle();
        String oldContent = card.getCardContent();

        // 카드 수정
        card.setCardTitle(request.getCardTitle());
        card.setCardContent(request.getCardContent());

        if(request.getMemberId() != null){
            Member assignedMember = memberRepository.findById(request.getMemberId())
                    .orElseThrow(()-> new NotFoundException("멤버가 없습니다."));
            card.setMember(assignedMember);
        }

        Card updateCard = cardRepository.save(card);

        logCardActivity(updateCard, Action.UPDATED, "제목: " + oldTitle + " -> " + updateCard.getCardTitle() +
                ", 내용 : " + oldContent + " -> " + updateCard.getCardContent() +
                ", 관리 멤버 :" + " -> " + updateCard.getMember().getMemberId());


        return new CardUpdateResponse(updateCard.getCardId(), updateCard.getCardTitle(), updateCard.getCardContent(), updateCard.getMember().getMemberId(), updateCard.getActivatedAt());

    }

    private void logCardActivity(Card card, Action action, String details) {
        CardActivityLog activityLog = new CardActivityLog();
        activityLog.setCard(card);
        activityLog.setAction(action);
        activityLog.setDetails(details);
        activityLog.setActiveTime(LocalDateTime.now());

        cardActivityLogRepository.save(activityLog);
    }
}
