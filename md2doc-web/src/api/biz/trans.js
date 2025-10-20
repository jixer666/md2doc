import request from '@/utils/request'

export function getDictDataPage(data) {
  return request({
    url: '/system/dictData/page',
    method: 'get',
    params: data
  })
}

export function previewTransMd(data) {
  return request({
    url: '/biz/trans/preview',
    method: 'post',
    data: data
  })
}
