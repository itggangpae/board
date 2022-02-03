package com.adamsoft.board;

import com.adamsoft.board.entity.Board;
import com.adamsoft.board.entity.Member;
import com.adamsoft.board.entity.Reply;
import com.adamsoft.board.repository.BoardRepository;
import com.adamsoft.board.repository.MemberRepository;
import com.adamsoft.board.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    //@Test
    public void insertMembers() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .password("1111")
                    .name("USER" + i)
                    .build();

            memberRepository.save(member);
        });
    }

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder().email("user"+i +"@aaa.com").build();
            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content...." + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }

    @Autowired
    private ReplyRepository replyRepository;

    //@Test
    public void insertReply() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            //1부터 100까지의 임의의 번호
            long bno  = (long)(Math.random() * 100) + 1;
            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("Reply......." +i)
                    .board(board)
                    .replyer("guest")
                    .build();
            replyRepository.save(reply);
        });
    }

    @Transactional
    //@Test
    public void readBoard() {
        Optional<Board> result = boardRepository.findById(100L); //데이터베이스에 존재하는 번호
        Board board = result.get();
        System.out.println(board);
        System.out.println(board.getWriter());
    }

    //@Test
    public void readReply() {
        Optional<Reply> result = replyRepository.findById(1L);
        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());
    }

    //@Test
    public void testReadWithWriter() {
        Object result = boardRepository.getBoardWithWriter(100L);
        Object[] arr = (Object[])result;
        System.out.println("-------------------------------------------------- ");
        System.out.println(Arrays.toString(arr));
    }

    //@Test
    public void testGetBoardWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(100L);
        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    //@Test
    public void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        result.get().forEach(row -> {
            Object[] arr = (Object[])row;
            System.out.println(Arrays.toString(arr));
        });
    }

    //@Test
    public void testWithDetail(){
        Object result = boardRepository.getBoardByBno(100L);
        Object[] arr = (Object[])result;
        System.out.println(Arrays.toString(arr));
    }

    //@Test
    public void testSearchl() {
        boardRepository.search1();
    }

    //@Test
    public void testSearchPage() {
        Pageable pageable = PageRequest.of(0,10,
                Sort.by("bno").descending()
                        .and(Sort.by("title").ascending()));
        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
    }

    //@Test
    public void testListByBoard() {
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno( Board.builder().bno(95L).build());
        replyList.forEach(reply -> System.out.println(reply));
    }


}
