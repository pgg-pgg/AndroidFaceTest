package pgg.androidfacetest.动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by PDD on 2018/2/28.
 */

public class DTDLTest {

    public static void main(String args[]) {
        //创建委托类实例
        WTLClass wtlClass=new WTLClass();
        //创建中介类实例
        ProxyHandler handler=new ProxyHandler(wtlClass);
        //动态创建代理类
        Subject subject= (Subject)Proxy.newProxyInstance(wtlClass.getClass().getClassLoader(), Subject.class.getInterfaces(), handler);
        //通过代理类对象调用代理类方法，实际上会转到invoke方法调用
        subject.doSomething();
    }

    //创建委托类
    static class WTLClass implements Subject{
        @Override
        public void doSomething() {
            System.out.println("干一些事情");
        }
    }

    //委托类实现的接口
    interface Subject{
        void doSomething();
    }


    //创建中介类
    static class ProxyHandler implements InvocationHandler{

        private Object object;

        public ProxyHandler(Object o) {
            this.object=o;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("方法调用前干的事");
            object=method.invoke(proxy,args);
            System.out.println("方法调用前干的事");
            return object;
        }
    }
}
