package com.roopy.netflix.repository;

import com.roopy.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {
    /**
     * 사용자정보 조회 및 권한목록 조회
     *
     * @param username - 사용자 ID
     * @return 사용자 ID에 해당하는 사용자 정보
     */
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByUsername(String username);
}