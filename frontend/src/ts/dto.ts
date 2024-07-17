export interface Page<TData> {
  totalPages: number
  totalElements: number
  first: boolean
  last: boolean
  size: number
  content: TData[]
  number: number
  sort: {
    empty: boolean
    unsorted: boolean
    sorted: boolean
  }
  pageable: {
    pageNumber: number
    pageSize: number
    sort: {
      empty: boolean
      unsorted: boolean
      sorted: boolean
    }
    offset: number
    unpaged: boolean
    paged: boolean
  }
  numberOfElements: number
  empty: boolean
}

export interface AuthenticationRequest {
  email: string
  password: string
}

export interface AuthenticateResponse {
  email: string
  nickname: string
  accessToken: string
}

export interface AuthenticationResponse {
  email: string
  nickname: string
  accessToken: string
}

export interface MemberResponse {
  email: string
  nickname: string
}

export interface TipResponseDTO {
  id: string
  title: string
  content: string
  writer: MemberResponse
  updatedAt: string
  createdAt: string
}
