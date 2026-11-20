package com.example.demo;

// 四个注解 + JdbcTemplate + BCryptPasswordEncoder

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;

import java.util.List;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
class PasswordMigrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void migratePlainPasswords() {
        // ① 造一个 BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // ② 要处理哪三个 id？   1 2 3
        List<Long> ids = jdbcTemplate.queryForList("SELECT id FROM user WHERE password NOT LIKE '$2a$%'", Long.class);
        // ③ 循环里：
        for (Long id : ids){
            //    查原密码 → 已经是 $2a$ 开头就跳过 → 否则加密 → 更新 → 打印行数
            String old = jdbcTemplate.queryForObject("select password from user where id = ?", String.class, id);   // 查
            String hash = passwordEncoder.encode(old);       // 加密
            int rows = jdbcTemplate.update("update user set password = ? where id = ?", hash, id);             // 写
            System.out.println("id=" + id + " 原值=" + old + " 行数=" + rows);

        }

    }


}
