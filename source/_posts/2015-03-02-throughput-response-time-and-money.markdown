---
layout: post
title: "Throughput, Response Time and Money!"
date: 2015-03-02 14:12:23 +0530
comments: true
categories: rant
---

For any successful web service that runs on scale, two of the most important factors that need to be considered are - Throughput and Response Time. *Throughput* is the total requests that your web service can churn out to the world, where as *Response Time* is the time taken for single request to be executed on your server. Both of these contribute to the final goal - serving better scale. 

However, there is a never ending debate about what is better, and when. 
<!--more-->

If you ask an engineer working at a startup, the answer would probably be Response Time, since they need to make do with a limited set of resources. Where as if you ask an Engineering Manager from a multi billion dollar giant, you would probably hear, "throw more machines at it." I'm not saying that folks from bigger companies do not build their services well, but it all boils down to the amount of time and money they have in hand. 

Scaling a system horizontally doesn't necessarily increase the response time. As very rightly pointed out [here](http://use-the-index-luke.com/sql/testing-scalability/response-time-throughput-scaling-horizontal), horizontal scaling is just like adding more lanes to a high traffic road. It need not necessarily make the traffic move faster, it just accomodates more traffic at any given time. But that would still serve the purpose. Because tuning your application to get that extra juice out of your apis, will take time and effort, and not everyone has it. Not every team, or business requirement caters to it. If business demands scale, easiest way would be to set up the service on an auto scaling architecture.

Let me give you an example, you wrote a service and it gives you a 300 ms response time. You believe you've done an OK job, you are not specifically proud of this, and you've done better jobs before. So, deadlines are approaching and you need to ship this out. Assuming that the IO on this service is minimum, and the 300 ms is just CPU processing, you have a throughput of 3 requests per second per core. You extrapolate your numbers and give your manager the bill - number of machines needed. He takes your word and haunts dev ops for the machines. Heard the story before? May be you have :).

{% pullquote %}
But, there is this little thing, that perfectionist conscience, that nags you in your dreams, asking you.. Have you tried it all? Is 300 ms the best your service can do? You know that it's not. You can definitely push it to a  200ms. That would boost your throughput by 66%. But, deadlines are approaching, you just take the easier route. You let the machines take care of it. {"
What do you do, you throw more machines at it, and you move on with your life."}
{% endpullquote %}

I think, this problem goes deeper into the org philosophy. If the organisation's goals align to understand developer's conscience along with business needs, we will all be better developers, sleeping peacefully without dark haunting dreams about poorly written APIs. If not, well, we have a word for it, and its called tech debt. 

Thanks to [cmdr2](https://twitter.com/cmdr2) for contributing to this rant. This does not add any business value, and neither does it solve a problem, but hey, it's out there and that's weight off my chest.


