import axios from 'axios'
import storage from 'store'
import notification from 'ant-design-vue/es/notification'
import { VueAxios } from './axios'
import { ACCESS_TOKEN } from '@/store/mutation-types'

// 创建 axios 实例
const request = axios.create({
  // API 请求的默认前缀
  baseURL: process.env.VUE_APP_API_BASE_URL,
  timeout: 6000 // 请求超时时间
})

// 异常拦截处理器
const errorHandler = (error) => {
  console.log('err: ', error)
  notification.error({
    message: '请求错误',
    description: error.msg
  })
  return Promise.reject(error)
}

// request interceptor
request.interceptors.request.use(config => {
  const token = storage.get(ACCESS_TOKEN)
  // 如果 token 存在
  // 让每个请求携带自定义 token 请根据实际情况自行修改
  if (token) {
    config.headers['access_token'] = token
  }
  return config
}, errorHandler)

// response interceptor
request.interceptors.response.use((response) => {
  console.log(response)
  const status = response.status
  if (status !== 200) {
    notification.error({
      message: '请求错误',
      description: response.error
    })
    return Promise.reject(response)
  }
  const res = response.data
  if (res.code !== 1) {
    if (res.code === 40102) {
      notification.error({
        message: '访问错误',
        description: res.msg
      })
    } else {
      notification.error({
        description: res.msg
      })
    }
    return Promise.reject(response)
  }
  return response.data
}, errorHandler)

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, request)
  }
}

export default request

export {
  installer as VueAxios,
  request as axios
}
