package swm.s3.coclimb.api.docs;

import swm.s3.coclimb.api.RestDocsTestSupport;

class UserControllerDocsTest extends RestDocsTestSupport {

//    @Mock
//    UserQuery userQuery;
//
//    @Test
//    @DisplayName("세션 정보 기반으로 유저를 조회하는 API")
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
//        ResultActions result = mockMvc.perform(get("/users/me")
//                        .session(session))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(username))
//                .andExpect(jsonPath("$.instagramUserId").value(instagramUserId));
//
//        // docs
//        result.andDo(document("user-read",
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint()),
//                responseFields(
//                        fieldWithPath("username").type(JsonFieldType.STRING)
//                                .description("사용자이름"),
//                        fieldWithPath("instagramUserId").type(JsonFieldType.NUMBER)
//                                .description("인스타그램 사용자 아이디")
//                )));
//    }
//
}