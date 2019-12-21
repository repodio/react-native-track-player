/**
 * Metro configuration for React Native
 * https://github.com/facebook/react-native
 *
 * @format
 */

const path = require("path");
const blacklist = require("metro-config/src/defaults/blacklist");

const reactNativeLib = path.resolve(__dirname, "..");

module.exports = {
  watchFolders: [path.resolve(__dirname, "node_modules"), reactNativeLib],

  resolver: {
    // https://github.com/facebook/metro/issues/7#issuecomment-508129053
    // https://github.com/facebook/react-native/issues/21310
    extraNodeModules: new Proxy({}, {
      get: (target, name) => path.join(process.cwd(), `node_modules/${name}`),
    }),
    blacklistRE: blacklist([
      new RegExp(`${reactNativeLib}/node_modules/react-native/.*`)
    ])
  },  
  
  projectRoot: path.resolve(__dirname),

  transformer: {
    getTransformOptions: async () => ({
      transform: {
        experimentalImportSupport: false,
        inlineRequires: false
      }
    })
  },
};