package kz.sparklab.repositoty;

import kz.sparklab.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
    TelegramUser findByUsername(String username);
    @Query("SELECT u FROM TelegramUser u WHERE u.isFollowedForNotification = true")
    List<TelegramUser> findAllFollowedUsers();
}
