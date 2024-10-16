package com.sparta.newneoboardbuddy.domain.board.repository;

import com.sparta.newneoboardbuddy.domain.board.entity.Board;
import com.sparta.newneoboardbuddy.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b join fetch b.boardLists bl  " +
            "where b.boardId = :boardId AND bl.listId = :listId")
    Optional<Board> findBySpaceIdWithJoinFetchBoard(Long boardId, Long listId);

    Optional<Board> findByBoardIdAndWorkspace(Long boardId, Workspace workspace);
}
