import request from '@/utils/request'
import fileRequest from '@/utils/fileRequest'

export function getTransPage(data) {
  return request({
    url: '/biz/trans/page',
    method: 'get',
    params: data
  })
}

export function getUserTransPage(data) {
  return request({
    url: '/biz/trans/getUserTransPage',
    method: 'get',
    params: data
  })
}

export function updateTrans(data) {
  return request({
    url: '/biz/trans',
    method: 'put',
    data: data
  })
}

export function addTrans(data) {
  return request({
    url: '/biz/trans',
    method: 'post',
    data: data
  })
}

export function deleteTrans(data) {
  return request({
    url: '/biz/trans',
    method: 'delete',
    data: data
  })
}

export function previewTransMd(data) {
  return request({
    url: '/biz/trans/preview',
    method: 'post',
    data: data
  })
}

export function exportTransMd(data) {
  return fileRequest({
    url: '/biz/trans/export',
    method: 'post',
    data: data
  })
}
