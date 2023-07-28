package swm.s3.coclimb.api.adapter.in.web.user;

import swm.s3.coclimb.api.ControllerTestSupport;

class UserControllerTest extends ControllerTestSupport {

//
//    @Test
//    @DisplayName("세션 정보 기반으로 유저를 조회한다.")
//    void getUserBySessionData() throws Exception {
//        // given
//        Long instagramUserId = 123456789L;
//        String username = "username";
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("instagramUserId", instagramUserId);
//        given(userQuery.getUserByInstagramUserId(instagramUserId))
//                .willReturn(User.builder()
//                .username(username)
//                .instagramUserId(instagramUserId)
//                .build());
//        // when, then
//        mockMvc.perform(get("/users/me")
//                        .session(session))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(username))
//                .andExpect(jsonPath("$.instagramUserId").value(instagramUserId));
//    }
}