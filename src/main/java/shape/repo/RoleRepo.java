package shape.repo;

import net.plsar.Dao;
import net.plsar.annotations.Repository;
import shape.model.Role;

@Repository
public class RoleRepo {

	Dao dao;

	public RoleRepo(Dao dao){
		this.dao = dao;
	}

	public int count() {
		String sql = "select count(*) from roles";
		int count = dao.getInt(sql, new Object[] { });
		return count;
	}

	public Role get(int id) {
		String sql = "select * from roles where id = [+]";
		Role role = (Role) dao.get(sql, new Object[] { id },Role.class);
		return role;
	}

	public Role get(String name) {
		String sql = "select * from roles where name = '[+]'";
		Role role = (Role) dao.get(sql, new Object[] { name }, Role.class);
		return role;
	}

	public void save(Role role) {
		String sql = "insert into roles (name) values('[+]')";
		dao.save(sql, new Object[]{
				role.getName()
		});
	}

}