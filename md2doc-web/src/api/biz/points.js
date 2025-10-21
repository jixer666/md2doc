import request from '@/utils/request'

export function getPointsPage(data) {
  return request({
    url: '/biz/points/page',
    method: 'get',
    params: data
  })
}

export function updatePoints(data) {
  return request({
    url: '/biz/points',
    method: 'put',
    data: data
  })
}

export function addPoints(data) {
  return request({
    url: '/biz/points',
    method: 'post',
    data: data
  })
}

export function deletePoints(data) {
  return request({
    url: '/biz/points',
    method: 'delete',
    data: data
  })
}
