package org.semanticwb.resources.sem.forumcat;

import java.util.Iterator;
import org.semanticwb.model.User;


public class Question extends org.semanticwb.resources.sem.forumcat.base.QuestionBase 
{
    public Question(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isUserSubscribed(User user) {
        boolean ret = false;
        Iterator<QuestionSubscription> subscriptions = QuestionSubscription.ClassMgr.listQuestionSubscriptionByQuestionObj(this);
        while (subscriptions.hasNext() && !ret) {
            QuestionSubscription subscription = subscriptions.next();
            if (subscription.getUserObj().getURI().equals(user.getURI())) {
                ret = true;
            }
        }

        return ret;
    }
}
