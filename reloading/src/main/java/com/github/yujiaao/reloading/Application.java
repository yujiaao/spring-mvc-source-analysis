package com.github.yujiaao.reloading;

public class Application {

    static volatile State state;

    enum State implements Runnable{
        INIT, LOADING, RUNNING;

        public void run() {
            switch (this){
                case LOADING:
                    System.out.println("doing a slow job ... ");

                    //这个构造函数很慢，模拟复杂项目初始化的情况。
                    try {
                        int count=0;
                        while(count++<20) {
                            Thread.sleep(1000);
                            System.out.println(count*5+"%");
                        }
                        state = RUNNING;
                        state.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    break;
                case RUNNING:
                    System.out.println("is normal running... ");
                    break;
                case INIT:
                    System.out.println("world staring... ");
                    break;
            }
        }
    }


    public static class Holder {
        private static Application INSTANCE = new Application();
        public static Application getInstance(){
            return INSTANCE;
        }
    }

    private Application()  {
        state = State.INIT;
        state.run();
    }



    public void load() {
        state = State.LOADING;
        new Thread(state).start();
    }

    public boolean isRunning() {
        return state==State.RUNNING;
    }
}
