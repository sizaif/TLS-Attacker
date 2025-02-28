/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.config.delegate;

import com.beust.jcommander.Parameter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ECPointFormat;
import de.rub.nds.tlsattacker.core.constants.NamedGroup;
import java.util.Collections;
import java.util.List;

public class NamedGroupsDelegate extends Delegate {

    @Parameter(names = "-point_formats", description = "Sets the elliptic curve point formats, divided by a comma")
    private List<ECPointFormat> pointFormats = null;
    @Parameter(names = "-named_group", description = "Named groups to be used, divided by a comma")
    private List<NamedGroup> namedGroups = null;

    public NamedGroupsDelegate() {
    }

    public List<ECPointFormat> getPointFormats() {
        if (pointFormats == null) {
            return null;
        }
        return Collections.unmodifiableList(pointFormats);
    }

    public void setPointFormats(List<ECPointFormat> pointFormats) {
        this.pointFormats = pointFormats;
    }

    public List<NamedGroup> getNamedGroups() {
        if (namedGroups == null) {
            return null;
        }
        return Collections.unmodifiableList(namedGroups);
    }

    public void setNamedGroups(List<NamedGroup> namedGroups) {
        this.namedGroups = namedGroups;
    }

    @Override
    public void applyDelegate(Config config) {
        if (namedGroups != null) {
            config.setDefaultClientNamedGroups(namedGroups);
            config.setDefaultServerNamedGroups(namedGroups);
            config.setDefaultSelectedNamedGroup(namedGroups.get(0));
        }
        if (pointFormats != null) {
            config.setDefaultServerSupportedPointFormats(pointFormats);
            config.setDefaultClientSupportedPointFormats(pointFormats);
        }
    }
}
