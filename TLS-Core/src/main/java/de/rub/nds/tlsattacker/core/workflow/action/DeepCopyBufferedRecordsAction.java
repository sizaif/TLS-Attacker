/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow.action;

import de.rub.nds.tlsattacker.core.exceptions.WorkflowExecutionException;
import de.rub.nds.tlsattacker.core.record.AbstractRecord;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import de.rub.nds.tlsattacker.core.workflow.action.executor.ActionOption;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement
public class DeepCopyBufferedRecordsAction extends CopyContextFieldAction {

    private static final Logger LOGGER = LogManager.getLogger();

    public DeepCopyBufferedRecordsAction() {

    }

    public DeepCopyBufferedRecordsAction(String srcConnectionAlias, String dstConnectionAlias) {
        super(srcConnectionAlias, dstConnectionAlias);
    }

    @Override
    protected void copyField(TlsContext src, TlsContext dst) {
        deepCopyRecords(src, dst);
    }

    @Override
    public boolean executedAsPlanned() {
        return isExecuted();
    }

    @Override
    public void reset() {
        setExecuted(false);
    }

    private void deepCopyRecords(TlsContext src, TlsContext dst) {
        LinkedList<AbstractRecord> recordBuffer = new LinkedList<>();
        ObjectOutputStream outStream;
        ObjectInputStream inStream;
        try {
            for (AbstractRecord record : src.getRecordBuffer()) {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                outStream = new ObjectOutputStream(stream);
                outStream.writeObject(record);
                outStream.close();
                inStream = new ObjectInputStream(new ByteArrayInputStream(stream.toByteArray()));
                AbstractRecord recordCopy = (AbstractRecord) inStream.readObject();

                recordBuffer.add(recordCopy);
                setExecuted(true);
            }
        } catch (IOException | ClassNotFoundException ex) {
            setExecuted(getActionOptions().contains(ActionOption.MAY_FAIL));
            LOGGER.error("Error while creating deep copy of recordBuffer");
            throw new WorkflowExecutionException(ex.toString());
        }

        dst.setRecordBuffer(recordBuffer);
    }
}
