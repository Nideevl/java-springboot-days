package library.repository;

import library.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberRepository {
    private Connection conn;

    public MemberRepository(Connection conn) { this.conn = conn; }

    public int save(Member member) throws SQLException {
        String sql = "INSERT INTO members(name, email) VALUES(?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, member.getName());
        stmt.setString(2,member.getEmail());
        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        if(keys.next()) {
            return keys.getInt(1);
        } else return 0;
    }

    public List<Member> findAll() throws SQLException {
        String sql = "SELECT * FROM members";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Member> members = new ArrayList<>();
        while(rs.next()) {
            members.add(new Member(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email")
            ));
        }
        return members;
    }

    public Optional<Member> findById(int memberId) throws SQLException {
        String sql = "SELECT * FROM members WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,memberId);
        ResultSet rs = stmt.executeQuery();

        if(rs.next())
            return Optional.of(new Member(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
        else return Optional.empty();
    }
}
