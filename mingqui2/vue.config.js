// vue.config.js

const webpack = require("webpack");

/**
 * @type {import('@vue/cli-service').ProjectOptions}
 */
module.exports = {
  publicPath: "/ui/",
  outputDir: "../ui/",
  productionSourceMap: true,

  configureWebpack: (config) => {
    // 禁用文件大小警告
    if (!config.performance) {
      config.performance = {};
    }
    config.performance.maxAssetSize = 30000000; // 整数类型（以字节为单位）
    config.performance.maxEntrypointSize = 30000000; // 整数类型（以字节为单位）
  },
};
