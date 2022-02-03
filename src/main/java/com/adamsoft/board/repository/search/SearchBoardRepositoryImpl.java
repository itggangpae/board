package com.adamsoft.board.repository.search;

import com.adamsoft.board.entity.Board;
import com.adamsoft.board.entity.QBoard;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }
    @Override
    public Board search1() {
        log.info("search1........................");
        QBoard board = QBoard.board;
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.select(board).where(board.bno.eq(1L));
        log.info("---------------------------");
        log.info(jpqlQuery);
        log.info("---------------------------");
        List<Board> result = jpqlQuery.fetch();
        return null;
    }
}
