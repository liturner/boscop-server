package de.turnertech.ows.servlet;

import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class Transaction extends BaseRequest {
    
    public static final QName QNAME = new QName(OwsContext.WFS_URI, "Transaction");

    private final List<TransactionAction> actions;

    private String lockId;

    private LockAction releaseAction;

    private SpatialReferenceSystem srsName;

    public Transaction() {
        this.actions = new LinkedList<>();
        this.releaseAction = LockAction.ALL;
    }

    /**
     * @return the actions
     */
    public List<TransactionAction> getActions() {
        return actions;
    }

    /**
     * @return the lockId
     */
    public String getLockId() {
        return lockId;
    }

    /**
     * @param lockId the lockId to set
     */
    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    /**
     * @return the releaseAction
     */
    public LockAction getReleaseAction() {
        return releaseAction;
    }

    /**
     * @param releaseAction the releaseAction to set
     */
    public void setReleaseAction(LockAction releaseAction) {
        this.releaseAction = releaseAction;
    }

    /**
     * @return the srsName
     */
    public SpatialReferenceSystem getSrsName() {
        return srsName;
    }

    /**
     * @param srsName the srsName to set
     */
    public void setSrsName(SpatialReferenceSystem srsName) {
        this.srsName = srsName;
    }

}
