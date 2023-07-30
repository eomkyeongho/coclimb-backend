package swm.s3.coclimb.api.application.port.out.instagram;

import swm.s3.coclimb.api.adapter.out.instagram.dto.LongLivedTokenResponse;
import swm.s3.coclimb.api.adapter.out.instagram.dto.ShortLivedTokenResponse;
import swm.s3.coclimb.domain.user.InstagramInfo;

public interface InstagramAuthPort {
    InstagramInfo getNewInstagramInfo(ShortLivedTokenResponse shortLivedTokenResponse);

    void updateInstagramToken(InstagramInfo instagram, ShortLivedTokenResponse shortLivedTokenResponse);

    ShortLivedTokenResponse getShortLivedTokenAndUserId(String code);

    LongLivedTokenResponse getLongLivedToken(String shortLivedToken);
}
