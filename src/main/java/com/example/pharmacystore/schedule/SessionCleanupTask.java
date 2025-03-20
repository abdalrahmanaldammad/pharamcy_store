package com.example.pharmacystore.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class SessionCleanupTask {

  private final DataSource dataSource;

  @Scheduled(fixedRate = 60000)
  public void cleanExpiredSessions() {
    String sql =
        "DELETTE FROM spring_session WHERE last_accessed_time <(EXTRACT(EPOCH FROM NOW())*1000-max-inactive_interval)";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      int rowDeleted = preparedStatement.executeUpdate();
      System.out.println("Deleted" + rowDeleted + "expired sessions");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
