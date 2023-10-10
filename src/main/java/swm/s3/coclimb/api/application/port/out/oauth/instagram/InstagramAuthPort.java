package swm.s3.coclimb.api.application.port.out.oauth.instagram;

import swm.s3.coclimb.api.adapter.out.oauth.instagram.dto.LongLivedTokenResponse;
import swm.s3.coclimb.api.adapter.out.oauth.instagram.dto.ShortLivedTokenResponse;
import swm.s3.coclimb.domain.user.InstagramUserInfo;

public interface InstagramAuthPort {
    InstagramUserInfo getNewInstagramUserInfo(ShortLivedTokenResponse shortLivedTokenResponse);

    void updateInstagramToken(InstagramUserInfo instagram, ShortLivedTokenResponse shortLivedTokenResponse);

    ShortLivedTokenResponse getShortLivedTokenAndUserId(String code);

    LongLivedTokenResponse getLongLivedToken(String shortLivedToken);
}
