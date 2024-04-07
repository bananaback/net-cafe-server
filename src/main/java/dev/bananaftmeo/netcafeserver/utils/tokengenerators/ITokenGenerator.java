package dev.bananaftmeo.netcafeserver.utils.tokengenerators;

import dev.bananaftmeo.netcafeserver.models.ApplicationUser;

public interface ITokenGenerator {
    String generateToken(ApplicationUser user);
}