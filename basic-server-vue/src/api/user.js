import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/account/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/sys/user/info',
    method: 'get'

  })
}

export function logout() {
  return request({
    url: '/account/logout',
    method: 'post'
  })
}
