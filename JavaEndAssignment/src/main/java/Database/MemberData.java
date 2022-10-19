package Database;

import Model.Member;
import Model.Person;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberData {
    private final List<Member> members;

    public MemberData() {
        Member memberPiet= new Member(LocalDate.of(2000,06,21),"Piet"," de Vries",32);
        Member memberBijay= new Member(LocalDate.of(2001,10,26),"Bijay","Sapkota",33);
        Member memberDaniel= new Member(LocalDate.of(1990,10,05),"Daniel","de Vries",34);
        members = new ArrayList<Member>();
        members.add(memberPiet);
        members.add(memberDaniel);
        members.add(memberBijay);
    }
    public List<Member> getMembers() {
        return members;
    }
    public void addMembers(Member member) {
        members.add(member);
    }
    public void removeMembers(Member member) {
        members.remove(member);
    }
    public Member getMemberById(int identifier) {
        Member returningMember = null;
        for (Member member : members) {
            if (member.getIdentifier() == identifier) {
                returningMember= member;
            }
        }
        return returningMember;
    }
    public void updateMember(Member member){
        member.setFirstName("Guru");
    }
}
