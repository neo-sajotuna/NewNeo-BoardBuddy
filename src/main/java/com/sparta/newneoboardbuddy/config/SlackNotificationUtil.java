package com.sparta.newneoboardbuddy.config;

import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;
import com.sparta.newneoboardbuddy.domain.comment.entity.Comment;
import com.sparta.newneoboardbuddy.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j(topic = "notice")
public class SlackNotificationUtil {
    @Value("${slack.webhook.url}")
    private String web_url;

    private final String PAY_LOAD_HEAD = "{ \"text\" : \" ";
    private final String PAY_LOAD_TAIL = "\"}";

    private final Slack slack;

    public SlackNotificationUtil() {
        slack = Slack.getInstance();
    }

    /**
     * 유저가 가입 했을 때 User 정보를 payload로 만들어 Slack에 전송하는 메서드
     * @param user 신규 가입된 유저
     */
    public void sendNewUser(User user) {
        String payload = String.format(PAY_LOAD_HEAD +
                "신규 가입 발생 : " +
                "id : %d," +
                "email : %s," +
                "userRole : %s" +
                PAY_LOAD_TAIL,  user.getId(), user.getEmail(), user.getUserRole().name());

        sendNotice(payload);
    }

    /**
     * 댓글이 작성 되엇을 때 댓글 정보와 작성자 정보를 payload로 만들어 Slack에 전송하는 메서드
     * @param comment Comment 작성된 댓글
     * @param user user 댓글 작성자
     */
    public void sendNewComment(Comment comment, User user) {
        String payload = String.format(PAY_LOAD_HEAD +
                "신규 댓글 발생 : " +
                "고유번호 : %d " +
                "내용 : %s " +
                "작성자 : %s " +
                PAY_LOAD_TAIL,  comment.getCommentId(), comment.getComment(), user.getEmail());

        sendNotice(payload);
    }

    /**
     * Slack에 Payload에 담긴 메시지를 전송하는 메서드
     * @param payload Slack에 전송할 알림/메시지
     */
    private void sendNotice(String payload) {
        try {
            WebhookResponse response = slack.send(web_url, payload);
            log.info(response.getMessage());
            log.info(response.getBody());
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
