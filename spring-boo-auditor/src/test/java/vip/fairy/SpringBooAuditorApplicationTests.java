package vip.fairy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import vip.fairy.entity.User;
import vip.fairy.repisitory.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class SpringBooAuditorApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Test
  @WithMockUser(username = "test-user")
  void testAuditingOnCreate() {
    User user = new User();
    user.setUsername("john.doe");
    user.setPassword("password");
    user.setEmail("john@example.com");

    User savedUser = userRepository.save(user);

    assertNotNull(savedUser.getCreatedBy());
    assertEquals("test-user", savedUser.getCreatedBy());
    assertNotNull(savedUser.getCreatedDate());
    assertTrue(savedUser.getCreatedDate().isBefore(LocalDateTime.now()));

    assertNotNull(savedUser.getLastModifiedBy());
    assertEquals("test-user", savedUser.getLastModifiedBy());
    assertNotNull(savedUser.getLastModifiedDate());
    assertEquals(savedUser.getCreatedDate(), savedUser.getLastModifiedDate());
  }

  @Test
  @WithMockUser(username = "test-user")
  void testAuditingOnUpdate() throws InterruptedException {
    User user = new User();
    user.setUsername("jane.doe");
    user.setPassword("password");
    user.setEmail("jane@example.com");

    User savedUser = userRepository.save(user);
    LocalDateTime initialCreateDate = savedUser.getCreatedDate();
    LocalDateTime initialModifyDate = savedUser.getLastModifiedDate();

    // 等待一段时间确保时间戳有变化
    Thread.sleep(1000);

    savedUser.setEmail("jane.doe@example.com");
    User updatedUser = userRepository.save(savedUser);

    // 创建时间和创建人不应改变
    assertEquals(initialCreateDate, updatedUser.getCreatedDate());
    assertEquals("test-user", updatedUser.getCreatedBy());

    // 修改时间和修改人应更新
    assertNotEquals(initialModifyDate, updatedUser.getLastModifiedDate());
    assertTrue(updatedUser.getLastModifiedDate().isAfter(initialModifyDate));
    assertEquals("test-user", updatedUser.getLastModifiedBy());
  }

  @Test
  @WithMockUser(username = "user", password = "user", roles = "USER")
  public void testUserCreate() throws Exception {
    String courseJson = "{\"name\":\"Test Course\",\"description\":\"Test Desc\"}";
    mockMvc.perform(
        post("/api/courses")
            .content(courseJson)
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username = "admin", password = "admin", roles = "USER")
  public void testAdminCreate() throws Exception {
    String courseJson = "{\"name\":\"Test Course\",\"description\":\"Test Desc\"}";
    mockMvc.perform(
        post("/api/courses")
            .content(courseJson)
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
  public void testAdminAccess() throws Exception {
    mockMvc.perform(get("/api/courses")).andExpect(status().isOk());
  }

}
