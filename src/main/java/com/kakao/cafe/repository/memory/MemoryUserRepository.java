package com.kakao.cafe.repository.memory;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final List<User> users = new CopyOnWriteArrayList<>();

    @Override
    public void save(User user) {
        if (isExistUserId(user.getUserId())) {
            updateUser(user);
            return;
        }
        users.add(user);
    }

    @Override
    public User findByUserId(String userId) {
        return users.stream()
                .filter(user -> user.isEqualsUserId(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public boolean isExistUserId(String userId) {
        return users.stream()
                .anyMatch(user -> user.isEqualsUserId(userId));
    }

    @Override
    public boolean isExistEmail(String email) {
        return users.stream()
                .anyMatch(user -> user.isEqualsEmail(email));
    }

    private void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (user.isEqualsUserId(users.get(i).getUserId())) {
                users.set(i, user);
            }
        }
    }

}
