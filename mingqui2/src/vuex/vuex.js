import { createStore } from "vuex";

// 创建一个新的 store 实例
const store = createStore({
  state() {
    return {
      userType: "normal",
      uid: 0,
      avatarUrl: "/ui/favicon.ico",
      nickname: "游客",
      attentionCount: 0,
      blackMap: new Map(),
    };
  },
  mutations: {
    setAdmin(state) {
      state.userType = "admin";
    },
    setByLoginVo(state, vo) {
      state.uid = vo.userSimpleRetVo.uid;
      state.avatarUrl = vo.userSimpleRetVo.avatarUrl;
      state.nickname = vo.userSimpleRetVo.nickname;
      state.userType = "normal";
      state.blackMap = new Map();

      for (let blackid of vo.blackList) {
        if (typeof blackid == "string") {
          blackid = parseInt(blackid);
        }
        state.blackMap.set(blackid, true);
      }
    },
    setByUserRetVo(state, userRetVo) {
      state.uid = userRetVo.uid;
      state.avatarUrl = userRetVo.avatarUrl;
      state.nickname = userRetVo.nickname;
      state.attentionCount = userRetVo.attentionCount;
    },
    setAttentionCount(state, count) {
      state.attentionCount = count;
    },
    setBlackMap(state, idBlackMap) {
      state.blackMap = new Map();
      for (let key in idBlackMap) {
        state.blackMap.set(idBlackMap[key].uid, true);
      }
    },
    setAvatar(state, url) {
      state.avatarUrl = url;
    },
    clearLoginInfo(state) {
      state.userType = "normal";
      state.uid = 0;
      state.avatarUrl = "/ui/favicon.ico";
      state.nickname = "游客";
      state.attentionCount = 0;
      state.blackMap = new Map();
    },
  },
});

export { store };
