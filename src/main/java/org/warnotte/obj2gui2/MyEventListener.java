package org.warnotte.obj2gui2;

import java.util.EventListener;

/**
 * 
 * @author Warnotte Renaud
 *
 */
public interface MyEventListener extends EventListener {
    public void myEventOccurred(MyChangedEvent evt);
}