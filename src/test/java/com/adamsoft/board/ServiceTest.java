package com.adamsoft.board;

import com.adamsoft.board.dto.BoardDTO;
import com.adamsoft.board.dto.PageRequestDTO;
import com.adamsoft.board.dto.PageResultDTO;
import com.adamsoft.board.dto.ReplyDTO;
import com.adamsoft.board.service.BoardService;
import com.adamsoft.board.service.ReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private BoardService boardService;

    //@Test
    public void testRegister() {

        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@aaa.com")  //현재 데이터베이스에 존재하는 회원 이메일
                .build();

        Long bno = boardService.register(dto);
        System.out.println(bno);
    }

    //@Test
    public void testList() {

        //1페이지 10개
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }

    }

    //@Test
    public void testGet() {
        Long bno = 100L;
        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);
    }

    //@Test
    public void testRemove() {
        boardService.removeWithReplies(1L);
    }

    //@Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경합니다.2")
                .content("내용 변경합니다.2")
                .build();

        boardService.modify(boardDTO);
    }

    @Autowired
    private ReplyService replyService;

    @Test
    public void testGetList() {
        Long bno = 95L;//데이터베이스에 존재하는 번호
        List<ReplyDTO> replyDTOList = replyService.getList(bno);
        replyDTOList.forEach(replyDTO -> System.out.println(replyDTO));
    }

}
