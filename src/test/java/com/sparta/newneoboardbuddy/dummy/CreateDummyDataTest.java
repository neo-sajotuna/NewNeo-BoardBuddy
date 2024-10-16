package com.sparta.newneoboardbuddy.dummy;

import com.sparta.newneoboardbuddy.config.SlackNotificationUtil;
import com.sparta.newneoboardbuddy.domain.board.entity.Board;
import com.sparta.newneoboardbuddy.domain.card.entity.Card;
import com.sparta.newneoboardbuddy.domain.list.entity.BoardList;
import com.sparta.newneoboardbuddy.domain.member.entity.Member;
import com.sparta.newneoboardbuddy.domain.user.entity.User;
import com.sparta.newneoboardbuddy.domain.workspace.entity.Workspace;
import com.sparta.newneoboardbuddy.dummy.config.DataHub;
import com.sparta.newneoboardbuddy.dummy.config.DummyDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateDummyDataTest {
    @Autowired
    SlackNotificationUtil slackNotificationUtil;

    @Autowired
    DummyDataFactory dummyDataFactory;

    @Autowired
    DataHub datahub;

//    @Test
//    void contextLoads() throws IOException {
//        User user = new User();
//        ReflectionTestUtils.setField(user, "id", 0L);
//        ReflectionTestUtils.setField(user, "email", "test@a.com");
//        ReflectionTestUtils.setField(user, "userRole", UserRole.ROLE_ADMIN);
//
//        slackNotificationUtil.sendNewUser(user);
//    }

//    @Test
//    void createDummySet() {
//        int userSize = 10;
//        int workspaceSize = 10;
//        int boardSize = 10;
//        int boardListSize = 10;
//        int cardSize = 250000;
//
//        List<User> users = dummyDataFactory.createDummyUser(userSize);
//        List<Member> members = new ArrayList<Member>();
//        List<Workspace> workspaces = dummyDataFactory.createDummyWorkspaces(workspaceSize, users, members);
//        members = dummyDataFactory.saveMembers(members);
//        List<Board> boards = dummyDataFactory.createDummyBoard(boardSize, workspaces);
//        List<BoardList> boardLists = dummyDataFactory.createDummyBoardList(boardListSize, boards);
//        boardLists = dummyDataFactory.getBoardListsFetchJoin();
//        List<Card> cards = dummyDataFactory.createDummyCard(cardSize / 2, members, workspaces, boardLists);
//    }
//
//    @Test
//    void createDummyUsers() {
//        int userSize = 10;
//        List<User> users = dummyDataFactory.createDummyUser(userSize);
//
//        assertEquals(users.size(), userSize);
//    }
//
//    @Test
//    void createDummyWorkspaces() {
//        int workspaceSize = 10;
//        List<User> users = datahub.getAllUsers();
//        List<Member> members = new ArrayList<Member>();
//
//        List<Workspace> workspaces = dummyDataFactory.createDummyWorkspaces(workspaceSize, users, members);
//        List<Member> savedMembers = dummyDataFactory.saveMembers(members);
//
//        assertEquals(workspaces.size(), workspaceSize);
//        assertEquals(savedMembers.size(), members.size());
//    }
//
//    @Test
//    void createDummyBoards() {
//        int boardSize = 10;
//        List<Workspace> workspaces = datahub.getAllWorkspaces();
//        List<Board> boards = dummyDataFactory.createDummyBoard(boardSize, workspaces);
//
//        assertEquals(boards.size(), boardSize);
//    }
//
//    @Test
//    void createBoardList() {
//        int boardListSize = 10;
//
//        List<Board> boards = datahub.getAllBoards();
//        List<BoardList> boardLists = dummyDataFactory.createDummyBoardList(boardListSize, boards);
//
//        assertEquals(boardLists.size(), boardListSize);
//    }
//
//    @Test
//    void createDummyCards() {
//        int cardSize = 125000;
//        List<Workspace> workspaces = datahub.getAllWorkspaces();
//        List<Board> boards = datahub.getAllBoards();
//        List<BoardList> boardLists = dummyDataFactory.getBoardListsFetchJoin();
//        List<Member> members = datahub.getAllMembers();
//
//        List<Card> cards = dummyDataFactory.createDummyCard(cardSize, members, workspaces, boardLists);
//
//        assertEquals(cards.size(), cardSize);
//    }

}
