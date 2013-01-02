package org.yfyang.osy.record;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.yfyang.osy.record.generate.GenerateService;
import org.yfyang.osy.record.generate.IGenerateService;
import org.yfyang.osy.record.model.JdbcInfo;
import org.yfyang.osy.record.model.ProjectInfo;
import org.yfyang.osy.record.util.ResourceUtils;

/** 系统入口 */
public class App {

	private static final ThreadLocal<ResourceUtils> RESOURCE_UTILS_THREAD_LOCAL = new ThreadLocal<ResourceUtils>();

	public static void main(String[] args) {
		String output = gen(args);
		if (StringUtils.isEmpty(output)) {
			return;
		}
		IGenerateService service = new GenerateService();
		service.generateFile(output);
	}

	public static String gen(String[] args) {
		final ResourceBundle resourceBundle = ResourceBundle.getBundle("message", Locale.getDefault());
		final Options options = new Options();
		options.addOption("h",false,resourceBundle.getString("help"));
		options.addOption("s", true, resourceBundle.getString("host"));
		options.addOption("t", true, resourceBundle.getString("type"));
		options.addOption("d", true, resourceBundle.getString("db"));
		options.addOption("u", true, resourceBundle.getString("user"));
		options.addOption("p", true, resourceBundle.getString("pwd"));
		options.addOption("o", true, resourceBundle.getString("output"));

		options.addOption("port", false, resourceBundle.getString("port"));
		options.addOption("package", false, resourceBundle.getString("package"));
		options.addOption("project", false, resourceBundle.getString("project"));

		final BasicParser parser = new BasicParser();

		CommandLine cli;

		try {
			cli = parser.parse(options, args);
			if (cli.getOptions().length > 0) {
				if (cli.hasOption('h')) {
					//help
					HelpFormatter hf = new HelpFormatter();
					hf.printHelp("Options", options);
				} else {
					final JdbcInfo jdbcInfo = new JdbcInfo();
					String dbType = cli.getOptionValue("t", "mysql");
					dbType = dbType.toLowerCase();
					String url_format = null;
					if (StringUtils.equals("mysql", dbType)) {
						jdbcInfo.setDriver("com.mysql.jdbc.Driver");
						url_format = "jdbc:mysql://%s:%s/%s?useUnicode=true&&characterEncoding=utf-8";
					} else if (StringUtils.equals("oracle", dbType)) {

					}

					String serverIp = cli.getOptionValue("s", "localhost");
					String port = cli.getOptionValue("port", "3306");
					String db_instance = cli.getOptionValue("d");
					String url = String.format(url_format, serverIp, port, db_instance);
					jdbcInfo.setUrl(url);

					String dbUser = cli.getOptionValue("u");
					jdbcInfo.setUsername(dbUser);
					String dbPwd = cli.getOptionValue("p");
					jdbcInfo.setPassword(dbPwd);


					ProjectInfo projectInfo = new ProjectInfo();
					String packageName = cli.getOptionValue("package", "record");
					String project = cli.getOptionValue("project", "record");
					String cache = "org.mybatis.caches.ehcache.EhcacheCache";
					projectInfo.setPackageName(packageName);
					projectInfo.setProjectName(project);
					projectInfo.setCache(cache);
					projectInfo.setBizTable("*");
					projectInfo.setUser(System.getProperty("user.name"));
					RESOURCE_UTILS_THREAD_LOCAL.set(new ResourceUtils(jdbcInfo, projectInfo));

					return cli.getOptionValue("o", System.getProperty("user.dir"));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResourceUtils getInfo() {
		return RESOURCE_UTILS_THREAD_LOCAL.get();
	}
}
