import { useEffect, useState } from 'react'

import { useAppSelector } from '../app/hook.ts'

export const useSession = () => {
  const [session, setSession] = useState<
    'loading' | 'authenticated' | 'unauthenticated'
  >('loading')

  const store = useAppSelector((state) => state.auth)

  useEffect(() => {
    setSession(store.accessToken !== null ? 'authenticated' : 'unauthenticated')
  }, [store.accessToken])

  return {
    session,
  }
}
