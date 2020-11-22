import Cookies from 'js-cookie'

const TokenKey = 'access_token'
const RefreshKey = 'refresh_token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function getRefreshKey() {
  return Cookies.get(RefreshKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function setLoginToken(access, refresh) {
  const k1 = Cookies.set(RefreshKey, refresh)
  const k2 = Cookies.set(TokenKey, access)
  console.log(`${k1} aa ${k2}`)
  return k1 && k2
}

export function removeToken() {
  const k1 = Cookies.remove(TokenKey)
  const k2 = Cookies.remove(RefreshKey)
  return k1 && k2
}
