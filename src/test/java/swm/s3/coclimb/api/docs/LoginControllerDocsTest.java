package swm.s3.coclimb.api.docs;

import swm.s3.coclimb.api.RestDocsTestSupport;

class LoginControllerDocsTest extends RestDocsTestSupport {

//    @Test
//    @DisplayName("/login/instagram 으로 접속하면 인스타그램 로그인 페이지로 리다이렉트 된다.")
//    void redirectInstagramLoginPage() throws Exception {
//        // given
//        given(autoLoginInterceptor.preHandle(any(), any(), any()))
//                .willReturn(false);
//        // when, then
//        ResultActions results = mockMvc.perform(get("/login/instagram"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(
//                        result -> result.getResponse().getRedirectedUrl().contains("https://api.instagram.com/oauth/authorize?client_id="))
//                .andExpect(
//                        result -> result.getResponse().getRedirectedUrl().contains("&redirect_uri="))
//                .andExpect(
//                        result -> result.getResponse().getRedirectedUrl().contains("&scope=user_profile,user_media&response_type=code"));
//
//        // docs
//        results.andDo(document("login-redirect",
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint())));
//    }

}