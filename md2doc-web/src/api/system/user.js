import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/system/login',
    method: 'post',
    data
  })
}

export function register(data) {
  return request({
    url: '/system/register',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/system/user/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/system/logout',
    method: 'post'
  })
}

export function getUserPage(data) {
  return request({
    url: '/system/user/page',
    method: 'get',
    params: data
  })
}

export function getUserRole(userId) {
  return request({
    url: '/system/user/getUserRole/' + userId,
    method: 'get'
  })
}

export function saveUserRole(data) {
  return request({
    url: '/system/user/saveUserRole',
    method: 'post',
    data
  })
}

export function resetPassword(data) {
  return request({
    url: '/system/user/resetPassword',
    method: 'put',
    data
  })
}

export function addUser(data) {
  return request({
    url: '/system/user',
    method: 'post',
    data
  })
}

export function updateUser(data) {
  return request({
    url: `/system/user`,
    method: 'put',
    data
  })
}

export function getCaptchaImg(data) {
  return request({
    url: '/system/captcha',
    method: 'get',
    params: data
  })
}
