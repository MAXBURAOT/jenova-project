package com.angelis.tera.game.ai;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.angelis.tera.game.ai.desire.Desire;
import com.angelis.tera.game.models.visible.VisibleTeraObject;
import com.angelis.tera.game.services.ThreadPoolService;

public abstract class AI<O extends VisibleTeraObject> implements Runnable {
    
    protected final O visibleTeraObject;
    private final Queue<Desire> desires = new PriorityBlockingQueue<>();
    
    private Future<?> task;
    protected boolean scheduled = true;
    
    public AI(final O visibleTeraObject) {
        this.visibleTeraObject = visibleTeraObject;
    }

    @Override
    public final void run() {
        synchronized(this.visibleTeraObject) {
            this.action();
        }
    }
    
    public final void schedule() {
        if (!this.scheduled) {
            task = ThreadPoolService.getInstance().scheduleAtFixedRate(this, 1000, 1000, TimeUnit.SECONDS);
        }
    }
    
    public final void cancel() {
        this.scheduled = false;
        this.task.cancel(false);
    }
    
    public void addDesire(final Desire desire) {
        final Iterator<Desire> itr = this.desires.iterator();
        while(itr.hasNext()) {
            final Desire iterated = itr.next();
            if (iterated.equals(desire)) {
                itr.remove();
                
                // Increase this desire
                if (desire != iterated) {
                    desire.increaseDesirePower(iterated.getDesirePower());
                }
                
                break;
            }
        }
        
        this.desires.add(desire);
    }
    
    public abstract void action();
    public abstract void onDeath(VisibleTeraObject killer);
}
