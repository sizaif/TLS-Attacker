/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package tls.rub.evolutionaryfuzzer;

/**
 * The Controller controls the genereal Programm. It links the Components
 * together and starts and stop the Fuzzing Process. I am unsure if it is ever
 * necessarry to create more than one Implementation.
 *
 * @author Robert Merget - robert.merget@rub.de
 */
public abstract class Controller
{

    //Is the Fuzzing Process running?

    protected boolean isRunning;

    /**
     * Starts the Fuzzer
     */
    public abstract void startFuzzer();

    /**
     * Stops the Fuzzer
     */
    public abstract void stopFuzzer();

    /**
     * Is the Fuzzer currently running?
     *
     * @return if the Fuzzer is running
     */
    public boolean isRunning()
    {
        return isRunning;
    }

}
