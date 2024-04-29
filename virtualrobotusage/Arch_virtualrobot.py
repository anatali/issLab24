### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('virtualrobotArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxvrqak', graph_attr=nodeattr):
          vrqak=Custom('vrqak','./qakicons/symActorWithobjSmall.png')
          vrobserver=Custom('vrobserver','./qakicons/symActorSmall.png')
          vrqakusage=Custom('vrqakusage','./qakicons/symActorSmall.png')
     vrqak >> Edge( label='vrinfo', **eventedgeattr, decorate='true', fontcolor='red') >> vrqak
     vrqak >> Edge( label='sonardata', **eventedgeattr, decorate='true', fontcolor='red') >> vrqak
     sys >> Edge( label='vrinfo', **evattr, decorate='true', fontcolor='darkgreen') >> vrqak
     sys >> Edge( label='sonardata', **evattr, decorate='true', fontcolor='darkgreen') >> vrobserver
     sys >> Edge( label='vrinfo', **evattr, decorate='true', fontcolor='darkgreen') >> vrobserver
     vrqakusage >> Edge(color='magenta', style='solid', decorate='true', label='<step<font color="darkgreen"> stepdone stepfailed</font> &nbsp; >',  fontcolor='magenta') >> vrqak
diag
