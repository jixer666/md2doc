import request from '@/utils/request'

export function getPointsFlowPage(data) {
  return request({
    url: '/biz/pointsFlow/page',
    method: 'get',
    params: data
  })
}

export function getUserPointsFlowPage(data) {
  return request({
    url: '/biz/pointsFlow/getUserPointsFlowPage',
    method: 'get',
    params: data
  })
}

export function updatePointsFlow(data) {
  return request({
    url: '/biz/pointsFlow',
    method: 'put',
    data: data
  })
}

export function addPointsFlow(data) {
  return request({
    url: '/biz/pointsFlow',
    method: 'post',
    data: data
  })
}

export function deletePointsFlow(data) {
  return request({
    url: '/biz/pointsFlow',
    method: 'delete',
    data: data
  })
}
