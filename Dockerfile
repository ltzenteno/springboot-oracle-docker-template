# Use the official Oracle Database image as the base image
FROM container-registry.oracle.com/database/express:21.3.0-xe

# Set environment variables (replace with your values)
ENV ORACLE_SID=XE
ENV ORACLE_PDB=ORCLPDB1
ENV ORACLE_PWD=secret

# Copy your custom scripts, if any
# COPY ./scripts /opt/oracle/scripts

# Expose ports
EXPOSE 1521
EXPOSE 5500

# Set entry point to run Oracle Database
CMD ["/opt/oracle/runOracle.sh"]