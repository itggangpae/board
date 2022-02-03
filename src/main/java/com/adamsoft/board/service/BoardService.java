package com.adamsoft.board.service;

import com.adamsoft.board.dto.BoardDTO;
import com.adamsoft.board.dto.PageRequestDTO;
import com.adamsoft.board.dto.PageResultDTO;
import com.adamsoft.board.entity.Board;
import com.adamsoft.board.entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    public BoardDTO get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO boardDTO);

    default Board dtoToEntity(BoardDTO dto){

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) //int로 처리하도록
                .build();

        return boardDTO;

    }


}