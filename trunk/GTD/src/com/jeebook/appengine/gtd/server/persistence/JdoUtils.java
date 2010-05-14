package com.jeebook.appengine.gtd.server.persistence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class JdoUtils {
    private static PersistenceManagerFactory pmf =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private static ThreadLocal<PersistenceManager> threadLocalPm =
        new ThreadLocal<PersistenceManager>();

    public static PersistenceManager getPm() {
      PersistenceManager pm = threadLocalPm.get();
      if (pm == null) {
        pm = pmf.getPersistenceManager();
        threadLocalPm.set(pm);
      }
      return pm;
    }

    public static void closePm() {
      PersistenceManager pm = threadLocalPm.get();
      if (pm == null) {
        return;
      }
      if (!pm.isClosed()) {
        pm.close();
      }
      threadLocalPm.set(null);
    }
}
