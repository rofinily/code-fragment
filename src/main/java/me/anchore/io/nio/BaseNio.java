package me.anchore.io.nio;

/**
 * @author anchore
 * @date 2018/7/20
 */
abstract class BaseNio {
    NioConf conf;

    BaseNio(NioConf conf) {
        this.conf = conf;
    }
}