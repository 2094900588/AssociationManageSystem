import Vue from "vue";
import Vuex from "vuex";
import userapi from '@/api/page/user'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        currentPathName: '',
        user: {},
        token: ""
    },
    mutations: {
        setPath(state) {
            state.currentPathName = localStorage.getItem("currentPathName")
        },
        setToken(state, token) {
            state.token = token
        },
        setUserInfo(state, user) {
            state.user = user
        },
        logout(state) {
            state.user = {}
            state.token = ""
        }
    },
    actions: {
        setToken({ commit }, user) {
            user = user | localStorage.getItem("user")
            if (user) {
                commit('setToken', JSON.parse(user).token)
            }
        },
        getUserInfo({ commit }) {
            userapi.getinfo().then(res => {
                commit('setUserInfo', res.data)
            })
        },
        exit({ commit }) {
            commit('logout')
        }
    }
})

export default store