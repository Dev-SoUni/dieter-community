import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

const TipNewPage = () => {
  const navigate = useNavigate()

  const [title, setTitle] = useState<string>('')
  const [content, setContent] = useState<string>('')

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    if (!title) {
      alert('제목은 필수 입력 사항입니다.')
      return
    }
    if (!content) {
      alert('내용은 필수 입력 사항입니다.')
      return
    }

    try {
      await fetch('http://localhost:8080/api/tips', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title,
          content,
        }),
      })

      alert('꿀팁이 등록되었습니다.')
      navigate('/tips')
    } catch (e) {
      alert('죄송합니다. 관리자에게 문의주세요.')
    }
  }

  return (
    <div>
      <div>
        <form onSubmit={handleSubmit}>
          <div>
            <input
              type="text"
              placeholder="제목"
              required
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>
          <div>
            <textarea
              rows={10}
              placeholder="내용"
              required
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
          </div>
          <div>
            <button type="submit">등록</button>
          </div>
        </form>
      </div>
      <Link to="/tips">목록</Link>
    </div>
  )
}

export default TipNewPage
