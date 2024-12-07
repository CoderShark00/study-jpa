package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

//    @Test
//    public void 회원가입(){
//        //given
//        Member member = new Member();
//        member.setName("kim");
//        //when
//        Long saveId = memberService.join(member);
//        Member findMember = memberRepository.findById(saveId).get();
//        //then
//        Assertions.assertThat(member).isEqualTo(findMember);
//    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        //then
        Assertions.assertThatThrownBy(()->memberService.join(member2))
                .isInstanceOf(IllegalStateException.class);
    }
}