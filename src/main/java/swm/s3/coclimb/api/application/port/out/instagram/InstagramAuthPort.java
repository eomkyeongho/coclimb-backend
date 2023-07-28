package swm.s3.coclimb.api.application.port.out.instagram;

import swm.s3.coclimb.api.adapter.out.instagram.dto.LongLivedTokenResponse;
import swm.s3.coclimb.api.adapter.out.instagram.dto.ShortLivedTokenResponse;
import swm.s3.coclimb.domain.user.Instagram;

public interface InstagramAuthPort {
    Instagram getNewInstagram(ShortLivedTokenResponse shortLivedTokenResponse);

    void updateInstagramToken(Instagram instagram, ShortLivedTokenResponse shortLivedTokenResponse);

    ShortLivedTokenResponse getShortLivedTokenAndUserId(String code);

    LongLivedTokenResponse getLongLivedToken(String shortLivedToken);
}
