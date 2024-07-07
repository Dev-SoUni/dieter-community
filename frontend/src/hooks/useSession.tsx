import { useMemo } from 'react'

import { useAppSelector } from '../app/hook.ts'

export const useSession = () => {
  const authStore = useAppSelector((state) => state.auth)

  const isLoggedIn = useMemo(() => {
    return authStore.accessToken !== null
  }, [authStore.accessToken])

  return {
    isLoggedIn,
  }
}
